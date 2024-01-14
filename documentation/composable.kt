Text(
            text = "Char Unity",
            modifier = Modifier.padding(0.dp, 60.dp),
            color = Color(79, 81, 216, 100),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily.Monospace,
            fontSize = 56.sp
)


                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        Icons.Filled.Face,
                                        contentDescription = "Localized description",
                                        Modifier.clickable {
                                            navController.navigate("addfriend")
                                        }
                                    )
                                }
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        Icons.Filled.Email,
                                        contentDescription = "Localized description",
                                        Modifier.clickable {
                                            navController.navigate("home")
                                        }
                                    )
                                }