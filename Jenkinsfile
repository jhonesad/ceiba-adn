pipeline {
	//Donde se va a ejecutar el pipeline
	agent {
		label 'Slave_Induccion'
	}
	
	//Opciones específicas de Pipeline dentro del Pipeline   
	options {  
		buildDiscarder(logRotator(numToKeepStr: '3'))  
		disableConcurrentBuilds()   
	}
	
	//Una sección que define las herramientas “preinstaladas” en Jenkins   
	tools {     
		jdk 'JDK8_Centos' //Preinstalada en la Configuración del Master     
		gradle 'Gradle4.5_Centos' //Preinstalada en la Configuración del Master   
	}
	
	stages {
		stage('Checkout') {
			steps {
				echo "------------>Checkout<------------"
				checkout([
					$class: 'GitSCM',
					branches: [[name: '*/master']],
					doGenerateSubmoduleConfigurations: false,
					extensions: [],
					gitTool: 'Git_Centos',
					submoduleCfg: [],
					userRemoteConfigs:[[
						credentialsId: 'GitHub_jhonesad',
						url: 'https://github.com/jhonesad/ceiba-adn.git'
					]]
				])
			}
		}
		
		stage('Build project') { 
			steps { 
				echo "------------>Build project<------------"
				sh 'gradle --b ./ServicioBarberia/build.gradle clean'
				sh 'gradle --b ./ServicioBarberia/build.gradle build'
			}
		}
		
		stage('Unit Tests & Coverage') {       
			steps {         
				echo "------------>Unit Tests<------------"
				sh 'gradle --b ./ServicioBarberia/build.gradle test'
				/* sh 'gradle --b ./ServicioBarberia/build.gradle jacocoTestReport' */
			}
		}
		
		stage('Integration Tests') {
			steps {
				echo "------------>Integration Tests<------------"
			}
		}
		
		stage('Static Code Analysis') {       
			steps {
				echo '------------>Análisis de código estático<------------'         
				withSonarQubeEnv('Sonar') { 
					sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
				}
			}
		}
	}
	
	post {
		always {
			echo 'This will always run'
		}
		success {
			echo 'This will run only if successful'
			junit ​'build/test-results/test/*.xml' 
		}
		failure {
			echo 'This will run only if failed'
			mail( to: 'jhon.alcaraz@ceiba.com.co' ,
					body: "Build failed in Jenkins: Project: ${env.JOB_NAME} Build /n Number: ${env.BUILD_NUMBER} URL de build: ${env.BUILD_NUMBER}/n/n Please go to ${env.BUILD_URL} and verify the build", 
					subject: "ERROR CI: Project name → ${env.JOB_NAME}")
		}
		unstable {
			echo 'This will run only if the run was marked as unstable'
		}
		changed {
			echo 'This will run only if the state of the Pipeline has changed'       
			echo 'For example, if the Pipeline was previously failing but is now successful'
		} 
	}
}