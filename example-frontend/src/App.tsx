import "./App.css"
import { Counter } from "./features/counter/Counter"
import Login from "./features/login/Login"
import { Quotes } from "./features/quotes/Quotes"
import logo from "./logo.svg"

const App = () => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <Login />
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
