# Frontend Library

### License

Parts of this (specifically the loginSlice) have been pulled pretty directly from [Redux](https://github.com/reduxjs/redux-templates/blob/master)
As such please see the license displayed at [License](./LICENSE.md)

## Installation

### Prerequisites

This is designed to work with Redux and React

### Setup

1.  Run `pnpm install base-auth-client` to add the client to your library
2.  In your redux store import and add the loginSlice `import { loginSlice } from "base-auth-client/login"`
3.  In the entrypoint of the app, pass the store to the client and set the backend url for the client

        ```

        import { injectStore, injectBackendUrl } from "base-auth-client"
        ...
            injectStore(store)
            injectBackendUrl(import.meta.env.VITE_BACKEND_URL)
            render(...

        ```

## Usage

### Send Get and Post to the backend

```
import { get, post } from "base-auth-client"
const NetworkTests = () => {
    const getProtected = () => {
        get("/protected").then(res => console.log(res))
    }
    const postTest = () => {
        let bodyContent = { ads: "asd" }
        post("/test", { body: bodyContent, headers: {asd: "asd"} })
    }
}
```

### Get whether the user is currently logged in

```
  const isLoggedIn = useAppSelector(selectIsLoggedIn)
```

## What is it?

The frontend-library is designed to allow for easy interaction with the base-auth backend.

### Login buttons

In `base-auth-client/login`, the Login component renders a login button (if the user is not logged in), and a logout button (if the user is logged in)

### Login state

The loginSlice contains the state of whether the user is logged in, and is automatically updated by the base-auth-client library

### Auto-logout

When a user recieves a response from the backend that is unauthorised, it may contain a set-cookie header that sets the logged-in cookie to false. If this happens, then the state will be automatically set to logged out

### csrf security

The backend implements csrf protection, the client here takes a cookie from the backend and automatically applies it to POST requests

### includes credentials

This endpoint automatically includes credentials on all calls
