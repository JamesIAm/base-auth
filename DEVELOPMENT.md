# Working on BaseAuth
## Releasing the backend
### Releasing locally
To push a new version of the backend to the local
maven repo, run `./backend/gradlew publishToMavenLocal -p backend
`
## Releasing the frontend
### Releasing locally
Run `pnpm package`
Update the version in frontend to match the filepath `$PROJECT_ROOT/frontend-library/bin/??`
### Releasing to npm
Run `pnpm version patch` or `pnpm version minor` or `pnpm version major`
Then run `pnpm release`