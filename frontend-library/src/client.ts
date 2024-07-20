import { getCookie } from "./cookie";

const post = async (
	url: string,
	headers?: RequestInit["headers"],
	body?: RequestInit["body"]
) => {
	let headersWithCsrf = {
		...headers,
		"X-XSRF-TOKEN": getCookie("XSRF-TOKEN"),
	};
	const response = await fetch("http://localhost:8080" + url, {
		method: "POST",
		credentials: "include",
		headers: headersWithCsrf,
		body: body,
	});
	return response;
};

const get = async (url: string, headers?: RequestInit["headers"]) => {
	const response = await fetch("http://localhost:8080" + url, {
		method: "GET",
		credentials: "include",
		headers: headers,
	});
	return response;
};

export { post, get };
