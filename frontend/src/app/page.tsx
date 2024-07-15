import Image from "next/image";
import styles from "./page.module.css";
import { getServerSession } from "next-auth";
import { auth } from "./api/auth/[...nextauth]/auth";
import Login from "./components/Login";

export default async function Home() {
	const session = await auth();

	return (
		<main className={styles.main}>
			<Login />
		</main>
	);
}
