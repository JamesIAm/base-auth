"use client"
import { get, post } from "base-auth-client"
import { useAppDispatch } from "../app/hooks"
const NetworkTests = () => {
  const dispatch = useAppDispatch()

  const getProtected = async () => {
    get("/protected")
  }

  const getUnprotected = () => {
    get("/public")
  }

  const getUsers = () => {
    get("/users")
  }

  const getAdmin = () => {
    get("/admin/asd")
  }

  const getUserInfo = () => {
    get("/public/userinfo")
  }

  const postTest = () => {
    let bodyContent = { ads: "asd" }
    post("/test", { body: bodyContent })
  }

  const postPublic = () => {
    post("/public/test")
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
