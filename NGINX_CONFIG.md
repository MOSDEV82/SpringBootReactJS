# NGINX Configuration for Spring Boot React Template

This document provides instructions for configuring NGINX to properly handle the context path and static resources for the Spring Boot React Template application.

## Issue Description

When deploying the application on Tomcat with a context path (e.g., `/template-prod`), the following issues occur:

1. API calls from the frontend include the context path, resulting in URLs like `http://template.mosdev.org/template-prod/api/profile` instead of `http://template.mosdev.org/api/profile`.
2. The backend generates URLs that include the context path in responses, showing paths like `http://template.mosdev.org/template-prod/api/profile` even when accessed via `http://template.mosdev.org/api/profile`.
3. Static resources (JS, CSS) return 403 Forbidden errors.

## Solution

### 1. Backend Configuration

We've made the following changes to the backend configuration:

1. Set `server.servlet.context-path=/` in `application-prod.properties` to ensure the backend API endpoints are mapped correctly. This setting is crucial as it works together with the `X-Forwarded-Prefix` header in the NGINX configuration to prevent the context path from appearing in generated URLs.
2. Updated the CORS configuration in `SecurityConfig.java` to allow requests from `https://template.mosdev.org`.

### 2. NGINX Configuration

To properly handle the context path and static resources, you need to configure NGINX as follows:

```nginx
server {
    listen 80;
    server_name template.mosdev.org;

    # Redirect HTTP to HTTPS
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl;
    server_name template.mosdev.org;

    # SSL configuration
    ssl_certificate /path/to/your/certificate.crt;
    ssl_certificate_key /path/to/your/private.key;

    # Frontend static files
    location / {
        root /path/to/your/frontend/dist;
        try_files $uri $uri/ /index.html;
    }

    # API requests - rewrite to include the context path for Tomcat
    location /api/ {
        proxy_pass http://192.168.15.100:8081/template-prod/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        # Add X-Forwarded-Prefix header to tell Spring Boot the correct prefix
        proxy_set_header X-Forwarded-Prefix "";
    }

    # Swagger UI
    location /swagger-ui/ {
        proxy_pass http://192.168.15.100:8081/template-prod/swagger-ui/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        # Add X-Forwarded-Prefix header to tell Spring Boot the correct prefix
        proxy_set_header X-Forwarded-Prefix "";
    }

    # Assets (JS, CSS, images)
    location /assets/ {
        alias /path/to/your/frontend/dist/assets/;
        expires 1d;
        add_header Cache-Control "public";
    }
}
```

Replace the following placeholders with your actual values:
- `/path/to/your/certificate.crt` and `/path/to/your/private.key`: Your SSL certificate and key files
- `/path/to/your/frontend/dist`: The path to your frontend build directory
- `192.168.15.100:8081`: Your Tomcat server address and port

### 3. Explanation

This NGINX configuration:

1. Redirects HTTP to HTTPS for security
2. Serves static frontend files from the specified directory
3. Forwards API requests to the Tomcat server, adding the context path (`/template-prod`) to the URL
4. Serves static assets (JS, CSS, images) directly from the frontend build directory

The key parts of this configuration are:

1. The `/api/` and `/swagger-ui/` location blocks, which rewrite the URL to include the context path when forwarding requests to Tomcat. This allows the frontend to make API calls to `/api/...` without including the context path, while ensuring the requests reach the correct endpoint on the Tomcat server.

2. The `X-Forwarded-Prefix` header set to an empty string, which tells Spring Boot not to include the context path (`/template-prod`) when generating URLs in responses. This is crucial for preventing the backend from showing URLs like `template.mosdev.org/template-prod/api/profile` in responses.

## Alternative Deployment Options

Instead of using NGINX to handle the context path, you could also:

1. **Deploy the WAR file as the root application in Tomcat**:
   - Rename the WAR file to `ROOT.war` before deploying it to Tomcat
   - This will make Tomcat deploy the application at the root context path (`/`) instead of `/template-prod`
   - With this approach, you wouldn't need to add the context path in the NGINX proxy_pass directives

2. **Configure the context path in Tomcat**:
   - Create a context XML file in Tomcat's `conf/Catalina/localhost/` directory
   - Name it `ROOT.xml` and add the following content:
     ```xml
     <Context path="" docBase="/path/to/template-prod.war" />
     ```
   - This tells Tomcat to deploy the application at the root context path regardless of the WAR filename

## Testing

After applying these changes:

1. Restart the Tomcat server
2. Reload the NGINX configuration (`sudo nginx -s reload`)
3. Test the application by accessing `https://template.mosdev.org`
4. Verify that API calls work correctly and static resources load without 403 errors
5. Specifically check that accessing `https://template.mosdev.org/api/profile` shows the correct URL in the response (without `/template-prod`)
