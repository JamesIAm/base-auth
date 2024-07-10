import Image from "next/image";
import styles from "./page.module.css";
import { getServerSession } from "next-auth";
import { auth } from "./api/auth/[...nextauth]/auth";

export default async function Home() {
	const session = await auth();
	return (
		<main className={styles.main}>
			{session ? (
				<a href="/api/auth/signout">Logout</a>
			) : (
				<a href="/api/auth/signin">Sign in</a>
			)}
			<a href="http://localhost:8080/unprotected">Hit an unprotected endpoint</a>
			<a href="http://localhost:8080/protected">Hit a protected endpoint</a>

			<pre>{JSON.stringify(session, null, 2)}</pre>
		</main>
	);
}
