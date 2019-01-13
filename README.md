# Building
The build is configured with Gradle, using the Gradle Wrapper. To build, go to the command line and change to the cryptovalue project directory:

Windows: `.\gradlew build`

Non-Windows: `./gradlew build`

# Running
The compiled JAR is configured with the main class. To run, go to the command line and change to the cryptovalue project directory:

Windows: 'java build\libs\cryptovalue-0.1.jar'

non-Windows: 'java build/libs/cryptovalue-0.1.jar'

Expected output should be similar to:

~~~
Portfolio valuation in Euro
BTC = 30957.50
XRP = 549.6000
ETH = 507.40
Total Euro Value: 32014.5000
~~~
