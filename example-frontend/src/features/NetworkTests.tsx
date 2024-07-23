"use client"
import { get, post } from "base-auth-client"
import { useAppDispatch } from "../app/hooks"
import { checkIsLoggedIn } from "base-auth-client/login"
const NetworkTests = () => {
  const dispatch = useAppDispatch()

  const getProtected = async () => {
    get("/protected").then(response => {
      takeResponseAndCheckForUnauthenticated(response)
      return response
    })
  }

  const getUnprotected = () => {
    get("/public").then(response => {
      takeResponseAndCheckForUnauthenticated(response)
      return response
    })
  }

  const getUsers = () => {
    get("/users").then(response => {
      takeResponseAndCheckForUnauthenticated(response)
      return response
    })
  }

  const getAdmin = () => {
    get("/admin/asd").then(response => {
      takeResponseAndCheckForUnauthenticated(response)
      return response
    })
  }

  const getUserInfo = () => {
    get("/public/userinfo").then(response => {
      takeResponseAndCheckForUnauthenticated(response)
      return response
    })
  }

  const postTest = () => {
    let bodyContent = { ads: "asd" }
    post("/test", { body: bodyContent })
  }

  const postPublic = () => {
    post("/public/test")
  }

  const takeResponseAndCheckForUnauthenticated = (response: Response) => {
    if (response.status === 403 || response.status === 401) {
      dispatch(checkIsLoggedIn())
    }
  }
  // <div onClick={() => getCookie("XSRF-TOKEN")}>get cookie</div>

  return (
    <div>
      <div onClick={() => getProtected()}>getProtected</div>
      <div onClick={() => getUnprotected()}>getUnprotected</div>
      <div onClick={() => getAdmin()}>getAdmin</div>
      <div onClick={() => getUsers()}>getUsers</div>
      <div onClick={() => getUserInfo()}>getUserInfo</div>

      <div onClick={() => postTest()}>postTest</div>
      <div onClick={() => postPublic()}>postPublic</div>
    </div>
  )
}

export default NetworkTests
