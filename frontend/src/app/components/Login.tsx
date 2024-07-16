"use client";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { debug } from "console";
import { stat } from "fs";
import GoogleSignIn from "./GoogleSignIn";

type Props = {};

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
		const response = await fetch("http://localhost:8080/logout", {
			method: "POST",
			credentials: "include",
		}).then((res) => {
			checkIfLoggedIn();
			return res;
		});
		console.log(response);
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
		const response = await fetch("http://localhost:8080/unprotected", {
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
			<div onClick={() => getUsers()}>getUsers</div>

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
