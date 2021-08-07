pipeline {
	agent any
	tools {
    maven 'M3'
    }
	stages {
		stage('spring-build') { 
			steps {
    			dir('backend') {
      				sh "mvn clean package"
    			}
			}
		}
		stage('spring-deploy') {
			steps {
				script {
					// sh 'docker rm -f springboot-jar'
					sh 'docker build -t springboot-container ./backend'
                    sh 'docker run -it -d --name springboot-jar --network springboot-mysql -p 8080:8080 springboot-container bash'
				}
			}  
		}
		stage('vue-build-deploy') {   
            steps { 
				script {
					sh 'docker rm -f vue-nginx' 
					sh 'docker build -t dockerize-vuejs-app ./frontend'
					sh 'docker run --name vue-nginx -d -p 80:80 dockerize-vuejs-app' 
				} 
			}
		} 
	} 
}    