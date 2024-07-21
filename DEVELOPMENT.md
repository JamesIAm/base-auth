# Working on BaseAuth
## Releasing the backend
### Releasing locally
To push a new version of the backend to the local
maven repo, run `./backend/gradlew publishToMavenLocal -p backend
`
## Releasing the frontend
### Releasing locally
Run `pnpm -C frontend-client compile` from frontend-client to generate the up-to-date files
Update the version in frontend package.json to match the filepath `"base-auth-client": "../frontend-library/dist"`
run `pnpm -C frontend install` to update the node module
run `pnpm -C frontend dev` to run the app

```shell
pnpm -C frontend-library compile
pnpm -C frontend install
pnpm -C frontend dev
```
### Releasing to npm
Make sure everything is commited and pushed to origin
Run `pnpm version patch` or `pnpm version minor` or `pnpm version major`
Then run `pnpm release`