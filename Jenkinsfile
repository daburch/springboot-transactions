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
			}
		}

		stage('Deploy') {
			steps {
				echo 'Deploying....'
			}
		}
	}
}
