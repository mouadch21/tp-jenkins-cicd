
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Code source recupere depuis GitHub'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }

        stage('Package JAR') {
            steps {
                sh 'mvn -B package -DskipTests'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar'
            echo 'Compilation, tests et generation du JAR reussis !'
        }
        failure {
            echo 'Echec du pipeline CI/CD.'
        }
    }
}
