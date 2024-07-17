"use client";
import { post } from "./BackendClient";
import { getCookie } from "./cookie/Cookie";
import React, { useEffect, useState } from "react";

const Login = () => {
	const [isLoggedIn, setIsLoggedIn] = useState(false);
	const login = async () => {
		document.location = "http://localhost:8080/login";
	};

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
		const response = await fetch("http://localhost:8080/protected", {
			method: "GET",
			credentials: "include",
		}).then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getUnprotected = async () => {
		const response = await fetch("http://localhost:8080/public", {
			method: "GET",
		}).then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getUsers = async () => {
		const response = await fetch("http://localhost:8080/users", {
			method: "GET",
			credentials: "include",
		}).then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getAdmin = async () => {
		const response = await fetch("http://localhost:8080/admin/asd", {
			method: "GET",
			credentials: "include",
		}).then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const getUserInfo = async () => {
		const response = await fetch("http://localhost:8080/userinfo", {
			method: "GET",
			credentials: "include",
		}).then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
	};

	const postTest = async () => {
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

	return (
		<div>
			<div onClick={() => getProtected()}>getProtected</div>
			<div onClick={() => getUnprotected()}>getUnprotected</div>
			<div onClick={() => getAdmin()}>getAdmin</div>
			<div onClick={() => getUsers()}>getUsers</div>
			<div onClick={() => getUserInfo()}>getUserInfo</div>
			<div onClick={() => postTest()}>postTest</div>
			<div onClick={() => getCookie("XSRF-TOKEN")}>get cookie</div>

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
