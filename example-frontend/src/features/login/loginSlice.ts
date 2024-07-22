import { createAppSlice } from "../../app/createAppSlice"
import { checkIfLoggedIn } from "base-auth-client"

export interface LoginSliceState {
  isLoggedIn: boolean
}

const initialState: LoginSliceState = {
  isLoggedIn: checkIfLoggedIn(),
}

// If you are not using async thunks you can use the standalone `createSlice`.
export const loginSlice = createAppSlice({
  name: "login",
  // `createSlice` will infer the state type from the `initialState` argument
  initialState,
  // The `reducers` field lets us define reducers and generate associated actions
  reducers: create => ({
    check: create.reducer(state => {
      // Redux Toolkit allows us to write "mutating" logic in reducers. It
      // doesn't actually mutate the state because it uses the Immer library,
      // which detects changes to a "draft state" and produces a brand new
      // immutable state based off those changes
      state.isLoggedIn = checkIfLoggedIn()
    }),
  }),
  // You can define your selectors here. These selectors receive the slice
  // state as their first argument.
  selectors: {
    selectIsLoggedIn: loginStatus => loginStatus.isLoggedIn,
  },
})

// Action creators are generated for each case reducer function.
export const { check: checkIsLoggedIn } = loginSlice.actions

// Selectors returned by `slice.selectors` take the root state as their first argument.
export const { selectIsLoggedIn } = loginSlice.selectors
