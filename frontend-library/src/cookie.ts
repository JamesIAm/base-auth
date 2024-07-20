const getCookie = (desiredCookie: string) => {
	let allCookiesString = document.cookie;
	if (!allCookiesString) {
		return "";
	}
	let cookies = allCookiesString.split("; ");
	let cookieString = cookies.find((cookie) =>
		cookie.includes(desiredCookie + "=")
	);
	if (!cookieString) {
		return "";
	}
	let keyValueArr = cookieString.split("=");
	let value = keyValueArr[1];
	return value;
};

const checkIfLoggedIn = () => {
	return document.cookie.includes("logged-in=true");
};

export { getCookie, checkIfLoggedIn };
