import { getCookie } from "./cookie";

type HeadersProps = {
	headers?: RequestInit["headers"];
};

interface PostProps extends HeadersProps {
	body?: object;
}

const post = (url: string, props?: PostProps) => {
	let headersWithCsrf = {
		...props?.headers,
		"X-XSRF-TOKEN": getCookie("XSRF-TOKEN"),
	};
	let requestParams: RequestInit = {
		method: "POST",
		credentials: "include",
		headers: headersWithCsrf,
	};
	if (props?.body) {
		requestParams = { ...requestParams, body: JSON.stringify(props.body) };
	}
	return fetch("http://localhost:8080" + url, requestParams);
};

const get = (url: string, props?: HeadersProps) => {
	return fetch("http://localhost:8080" + url, {
		method: "GET",
		credentials: "include",
		headers: props?.headers,
	});
};

export { post, get };
