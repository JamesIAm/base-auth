## TODO
- Get email from github
- Create a create react app version for SPA's
- Redirect back to calling page after login
- Format userinfo endpoint
- Add DTOs and DAOs
- Publish to mvnrepo
- Set up auto changelog and version increments
- Add tests for various types of attempted breach
  - CSRF
  - Role validation
  - Authenticated endpoints
  - Wrong origin or request

## Running Instructions
#### Start the backend

```./backend/gradlew bootrun -p backend```
#### Start the frontend

```pnpm -C frontend dev```

## Setup instructions
### Adding base auth to a project
1. Add the following plugins to the build.gradle
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.0'
    id 'io.spring.dependency-management' version '1.1.5'
}
```
2. Add the following dependencies:
```groovy
dependencies {
    //...
    implementation("org.nahajski:baseauth:0.0.1")
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```
3. Import the BaseAuthSharedConfiguration in your SpringBootApplication main file e.g:
```java 
@SpringBootApplication
@Import(BaseAuthSharedConfiguration.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```
4. Create the config as defined in [Setting up config](#Setting-up-config)
5. When moving to prod, look to override

### Setting up config
#### application configuration

##### TL;DR - Set you application.yaml to the following:
```yaml
baseauth:
  frontend:
    url: http://localhost:5173
  pattern:
    unauthenticated: "/public/**"
    admin: "/admin/**"
spring:
  profiles:
    active: baseauth
  datasource:
    url: jdbc:h2:mem:mydb
    username: sa
    password: password
    driverClassName: org.h2.Driver
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console.enabled: true
```
##### Frontend URL
To ensure the authentication redirects you back to your frontend,
ensure you set the `baseauth.frontend.url` field in your application.yaml
```yaml
baseauth:
  frontend:
    url: <FRONTEND_URL>
```
For testing `<FRONTEND_URL>` will likely be `http://localhost:5173`

##### TODO: database readme
rn solution is add this too application.yaml:
```properties
spring:
  datasource:
    url: jdbc:h2:mem:mydb
    username: sa
    password: password
    driverClassName: org.h2.Driver
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console.enabled: true
```
##### TODO: Patterns
Everything not matching either of these will need a standard user role
(the default when someone new signs in)
```yaml
baseauth:
  pattern:
    unauthenticated: "/public/**"
    admin: "/admin/**"
```
##### spring.active.profiles
To pick up the application yaml that is defined in the 
base project set the active profiles to inclue `baseauth`
```yaml
spring:
  profiles:
    active: baseauth
```
#### .env.secret
Create a file called `.env.secret` in your repo
```properties
GOOGLE_CLIENT_ID=<GOOGLE_CLIENT_ID>
GOOGLE_CLIENT_SECRET=<GOOGLE_CLIENT_SECRET>
GITHUB_CLIENT_ID=<GITHUB_CLIENT_ID>
GITHUB_CLIENT_SECRET=<GITHUB_CLIENT_SECRET>
```

When running the Application, ensure you both `.env` files as Environment Variable sources in the Intellij run configuration.
### Google
To generate the client ID and secret for google:
1. Go to https://console.cloud.google.com/apis/dashboard
2. Create a new Project
3. Give it a name and click create
4. Once created, select the project
5. Go to the Oauth Consent Screen tab
6. Select Internal or External depending on how you want to test your app
7. Add a name, support email and Developer contact information
8. Click "Save and Continue"
9. Click add or remove scopes and add ".../auth/userinfo.email"
10. Click "Save and Continue"
11. Add a test user (your own email)
12. Click "Save and Continue"
13. Click back to dashboard
14. Go to credentials
15. Click create Credentials -> Oauth Client ID
16. Select Web Application as the application type
17. Name the application Spring Backend
18. In Authorised JavaScript Origins add the `<BACKEND_URL>` (http://localhost:8080 for testing purposes)
19. In Authorised redirect URIs add `<BACKEND_URL>/login/oauth2/code/google` (`http://localhost:8080/login/oauth2/code/google` for testing)
18. Click create
19. Copy the Client ID `<GOOGLE_CLIENT_ID>` and client secret `<GOOGLE_CLIENT_SECRET>` into the .env.secret file

### Github
To generate the client ID and secret for github
1. Go to https://github.com/settings/apps and create a new app.
2. Give the app a name
3. Set the homepage URL to `<BACKEND_URL>` (`http://localhost:8080` for testing purposes)
4. Set the callback url to `<BACKEND_URL>/login/oauth2/code/github` (`http://localhost:8080/login/oauth2/code/github` for testing)
5. Untick webhook as active
6. Under account permissions, set email addresses as read only
7. (When ready to deploy) Mark this app as able to be deployed on any Github Account (right at the bottom)
8. Click generate app
9. Copy the client ID into the .env.secret file above <GITHUB_CLIENT_ID>
10. Click "generate a new client secret" and copy the secret into the file above <GITHUB_CLIENT_SECRET>

### Deleting Google App

https://console.cloud.google.com/cloud-resource-manager
Go to this link, and delete

### Accessing Config directly

Rather than using environment variables feel free to change the app properties directly

Overriding the properties from [application.yaml](./backend/src/main/resources/application.yaml) should take precedence
over the values which are currently looking for env var values.