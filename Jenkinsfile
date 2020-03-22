pipeline {
	agent { 
		docker { 
			image 'gradle:jdk13' 
		}
	}

	stages {
		stage('Build') {
			steps {
				echo 'Building..'
				sh 'gradle build'
			}
		}

		stage('Test') {
			steps {
				echo 'Testing..'
				sh 'gradle test'
			}
		}

		stage('Deploy') {
			steps {
				echo 'Deploying....'
			}
		}
	}
}
