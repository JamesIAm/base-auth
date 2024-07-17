import { getCookie } from "./cookie/Cookie";

const post = async (url: string) => {
	console.log(getCookie("XSRF-TOKEN"));
	let headers = { "X-XSRF-TOKEN": getCookie("XSRF-TOKEN") };
	console.log(headers);
	const response = await fetch("http://localhost:8080" + url, {
		method: "POST",
		credentials: "include",
		headers: headers,
	}).then((response) => {
		// takeResponseAndCheckForUnauthenticated(response);
		return response;
	});
	return response;
};

export { post };
