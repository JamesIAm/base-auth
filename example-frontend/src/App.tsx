import "./App.css"
import { Counter } from "./features/counter/Counter"
import Login from "base-auth-client/login"
import { Quotes } from "./features/quotes/Quotes"
import logo from "./logo.svg"
import NetworkTests from "./features/NetworkTests"

const App = () => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <Login />
        <NetworkTests />
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
