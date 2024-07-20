"use client";
// import { getCookie } from "./cookie/Cookie";
import React, { useEffect, useState } from "react";
import { get, post } from "base-auth-client";
import { headers } from "next/headers";

const Login = () => {
	const [isLoggedIn, setIsLoggedIn] = useState(false);

	const loginWithGithub = async () => {
		document.location = "http://localhost:8080/oauth2/authorization/github";
	};

	const loginWithGoogle = async () => {
		document.location = "http://localhost:8080/oauth2/authorization/google";
	};

	const logout = async () => {
		post("/logout");
	};

	const getProtected = async () => {
		get("http://localhost:8080/protected").then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getUnprotected = () => {
		get("http://localhost:8080/public").then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getUsers = () => {
		get("http://localhost:8080/users").then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getAdmin = () => {
		get("http://localhost:8080/admin/asd").then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getUserInfo = () => {
		get("http://localhost:8080/userinfo").then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const postTest = () => {
		post("/test");
	};

	const takeResponseAndCheckForUnauthenticated = (response: Response) => {
		if (response.status === 403 || response.status === 401) {
			checkIfLoggedIn();
		}
	};

	const checkIfLoggedIn = () => {
		setIsLoggedIn(document.cookie.includes("logged-in=true"));
	};

	useEffect(() => {
		checkIfLoggedIn();
	}, []);
	// <div onClick={() => getCookie("XSRF-TOKEN")}>get cookie</div>

	return (
		<div>
			<div onClick={() => getProtected()}>getProtected</div>
			<div onClick={() => getUnprotected()}>getUnprotected</div>
			<div onClick={() => getAdmin()}>getAdmin</div>
			<div onClick={() => getUsers()}>getUsers</div>
			<div onClick={() => getUserInfo()}>getUserInfo</div>
			<div onClick={() => postTest()}>postTest</div>

			{isLoggedIn ? (
				<div onClick={() => logout()}>logout</div>
			) : (
				<>
					<div onClick={() => loginWithGithub()}>loginWithGithub</div>
					<div onClick={() => loginWithGoogle()}>loginWithGoogle</div>
				</>
			)}
		</div>
	);
};

export default Login;
