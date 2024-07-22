"use client"
// import { getCookie } from "./cookie/Cookie";
import { useEffect, useState } from "react"
import { get, post } from "base-auth-client"
import { useAppDispatch, useAppSelector } from "../../app/hooks"
import { checkIsLoggedIn, selectIsLoggedIn } from "./loginSlice"
const Login = () => {
  const dispatch = useAppDispatch()
  const isLoggedIn = useAppSelector(selectIsLoggedIn)

  const loginWithGithub = async () => {
    document.location = "http://localhost:8080/oauth2/authorization/github"
  }

  const loginWithGoogle = async () => {
    document.location = "http://localhost:8080/oauth2/authorization/google"
  }

  const logout = async () => {
    post("/logout").then(_unused => dispatch(checkIsLoggedIn()))
  }

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

      {isLoggedIn ? (
        <div onClick={() => logout()}>logout</div>
      ) : (
        <>
          <div onClick={() => loginWithGithub()}>loginWithGithub</div>
          <div onClick={() => loginWithGoogle()}>loginWithGoogle</div>
        </>
      )}
    </div>
  )
}

export default Login
