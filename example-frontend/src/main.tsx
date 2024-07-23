import React from "react"
import { createRoot } from "react-dom/client"
import { Provider } from "react-redux"
import App from "./App"
import { store } from "./app/store"
import "./index.css"
import { injectStore, injectBackendUrl } from "base-auth-client"

const container = document.getElementById("root")

if (container) {
  const root = createRoot(container)
  injectStore(store)
  injectBackendUrl(import.meta.env.VITE_BACKEND_URL)
  root.render(
    <React.StrictMode>
      <Provider store={store}>
        <App />
      </Provider>
    </React.StrictMode>,
  )
} else {
  throw new Error(
    "Root element with ID 'root' was not found in the document. Ensure there is a corresponding HTML element with the ID 'root' in your HTML file.",
  )
}
