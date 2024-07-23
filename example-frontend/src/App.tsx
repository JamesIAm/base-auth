import "./App.css"
import Login, { selectIsLoggedIn } from "base-auth-client/login"
import logo from "./logo.svg"
import NetworkTests from "./features/NetworkTests"
import { useAppSelector } from "./app/hooks"

const App = () => {
  const isLoggedIn = useAppSelector(selectIsLoggedIn)
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <Login />
        <NetworkTests />
        <h1>{isLoggedIn ? "User is logged in" : "User is not logged in"}</h1>
        {/* <Counter />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <Quotes /> */}
      </header>
    </div>
  )
}

export default App
