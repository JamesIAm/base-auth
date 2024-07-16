## TODO
- Return the user details to the frontend (name and email)
- Get email from github
- Create a create react app version for SPA's
- Add admin only endpoint


## Running Instructions
#### Start the backend

```./backend/gradlew bootrun -p backend```
#### Start the frontend

```pnpm -C frontend dev```

## Setup instructions
### Package
1. Refactor rename baseauth to something else
2. Go into settings.gradle and change the rootProject.name

### OAuth2 OIDC providers
Create a file called `.env.secret` in the same director as the readme
It's contents should be as follows:
```properties
GOOGLE_CLIENT_ID=<GOOGLE_CLIENT_ID>
GOOGLE_CLIENT_SECRET=<GOOGLE_CLIENT_SECRET>
GITHUB_CLIENT_ID=<GITHUB_CLIENT_ID>
GITHUB_CLIENT_SECRET=<GITHUB_CLIENT_SECRET>
```

When running the Application, ensure you include the `.env.secret` file as an 
Environment Variable source in the Intellij run configuration. There is an example commited
but the path is specific to my machine
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