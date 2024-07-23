# Releasing the frontend

## Releasing locally

Run `pnpm compile` from frontend-client to generate the up-to-date files
Update the version in frontend package.json to match the filepath `"base-auth-client": "../frontend-library/dist"`

```shell
pnpm compile
```

## Releasing to npm

Make sure everything is commited and pushed to origin
Run `pnpm version patch` or `pnpm version minor` or `pnpm version major`
Then run `pnpm release`
