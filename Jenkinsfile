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
    }

    post {
        success {
            echo 'Compilation et tests reussis !'
        }
        failure {
            echo 'Echec du pipeline CI/CD.'
        }
    }
}
