import { getCookie } from "./cookie";
import { checkIsLoggedIn } from "./login";

let store;

export const injectStore = (_store) => {
	store = _store;
};

type HeadersProps = {
	headers?: RequestInit["headers"];
};

interface PostProps extends HeadersProps {
	body?: object;
}

const post = (url: string, props?: PostProps) => {
	return getCsrfToken()
		.then((csrfToken) => {
			let headersWithCsrf = {
				...props?.headers,
				"X-XSRF-TOKEN": csrfToken,
			};
			let requestParams: RequestInit = {
				method: "POST",
				credentials: "include",
				headers: headersWithCsrf,
			};
			if (props?.body) {
				requestParams = {
					...requestParams,
					body: JSON.stringify(props.body),
				};
			}
			return fetch("http://localhost:8080" + url, requestParams);
		})
		.then((response) => {
			takeResponseAndCheckForUnauthenticated(response);
			return response;
		});
};

const getCsrfToken: () => Promise<string> = () => {
	let csrfToken = getCsrfCookie();
	if (!csrfToken) {
		return getUserInfo().then((_unused) => getCsrfCookie());
	}
	return new Promise<string>((resolve, _reject) => {
		resolve(csrfToken);
	});
};

const getCsrfCookie = () => {
	return getCookie("XSRF-TOKEN");
};
const get = (url: string, props?: HeadersProps) => {
	return fetch("http://localhost:8080" + url, {
		method: "GET",
		credentials: "include",
		headers: props?.headers,
	}).then((response) => {
		takeResponseAndCheckForUnauthenticated(response);
		return response;
	});
};

const getUserInfo = () => {
	return get("/public/userinfo");
};

const takeResponseAndCheckForUnauthenticated = (response: Response) => {
	if (response.status === 403 || response.status === 401) {
		store.dispatch(checkIsLoggedIn());
	}
};

export { post, get };
