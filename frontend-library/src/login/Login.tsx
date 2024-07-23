import { post } from "../client";
import { checkIsLoggedIn, selectIsLoggedIn } from "./loginSlice";
import { useDispatch, useSelector } from "react-redux";
const Login = () => {
	const dispatch = useDispatch();
	const isLoggedIn = useSelector(selectIsLoggedIn);

	const loginWithGithub = async () => {
		document.location = "http://localhost:8080/oauth2/authorization/github";
	};

	const loginWithGoogle = async () => {
		document.location = "http://localhost:8080/oauth2/authorization/google";
	};

	const logout = async () => {
		post("/logout").then((_unused) => dispatch(checkIsLoggedIn()));
	};

	return (
		<div>
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
