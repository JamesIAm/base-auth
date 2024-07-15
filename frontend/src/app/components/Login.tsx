"use client";
import React from "react";
import axios from "axios";

type Props = {};

const Login = () => {
	const login = async () => {
		const response = await fetch("http://localhost:8080/protected", {
			method: "GET",
			redirect: "follow",
			credentials: "include",
		}).then((res) => {
			response;
		});
	};

	const getProtected = async () => {
		const response = await fetch("http://localhost:8080/protected", {
			method: "GET",
			redirect: "follow",
			credentials: "include",
		}).then((response) => response);
		if (response.redirected) {
			document.location = response.url;
		}
	};

	const getUnprotected = () => {
		axios
			.get("http://localhost:8080/unprotected")
			.then((res) => console.log(res.data));
	};
	return (
		<div>
			``
			<div onClick={() => getProtected()}>getProtected</div>
			<div onClick={() => getUnprotected()}>getUnprotected</div>
			<div onClick={() => login()}>getUnprotected</div>
		</div>
	);
};

export default Login;
