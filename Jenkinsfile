pipeline {
  agent any
  environment {
    HEROKU_API_KEY = credentials('heroku-lukasjai')
    DEPLOYMENT_DEV = credentials('DEPLOYMENT_DEV')
    DEPLOYMENT_TEST = credentials('DEPLOYMENT_TEST')
    DEPLOYMENT_PROD = credentials('DEPLOYMENT_PROD')
  }
  triggers {
    cron('0 9 * * *')
  }
  parameters {
        booleanParam(name: 'DEPLOY_PROD', defaultValue: false, description: 'Manueller Trigger für Prod Deployment')
  }

  stages {
   stage('Run Unit Tests') {
          steps {
            sh './mvnw -Dtest=*ServiceTest test'
          }
          post {
            always {
              junit 'target/surefire-reports/*.xml'
            }
          }
        }

    stage('Run Controller Tests') {
          steps {
            sh './mvnw -Dtest=*ControllerTest test'
          }
          post {
            always {
              junit 'target/surefire-reports/*.xml'
            }
          }
        }

    stage('Build') {
      steps {
         sh 'docker build -t Lukasjai/masterarbeit_jenkis:latest .'
      }
    }

    stage('Login') {
      steps {
        sh 'echo $HEROKU_API_KEY | docker login --username=_ --password-stdin registry.heroku.com'
      }
    }

  stage('Deploy to Dev') {
        when {
                   triggeredBy 'TimerTrigger'
        }
        steps {
          echo "Deploying to Dev environment..."
           sh '''
                export APP_NAME=$DEPLOYMENT_DEV
                ./deploy.sh dev
              '''
        }
      }

  stage('Deploy to Test') {
        when {
          allOf {
            branch 'main'
            not { triggeredBy 'TimerTrigger' }
          }
        }
        steps {
              echo "Detected merge from development → Deploying to test"
              sh '''
                    export APP_NAME=$DEPLOYMENT_TEST
                    ./deploy.sh test
                  '''

          }
       }


  stage('Deploy to Prod') {
        when {
          expression { return params.DEPLOY_PROD == true }
        }
        steps {
          echo "Deploying to Production environment..."
            sh '''
                export APP_NAME=$DEPLOYMENT_PROD
                 ./deploy.sh prod
                 '''
        }
      }
    }

}
