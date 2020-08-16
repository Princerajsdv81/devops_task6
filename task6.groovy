job("Github_job1") {
  description("This will Pull Codes and scripts From GitHub repository and copies them into a folder in base system.")

  scm {
    github("princerajsdv81/devops_task6", "master")
  }

  triggers {
    scm("* * * * *")
  }

  steps {
       shell('''
    sudo cp -rvf * /home/jenkins_groovy
    ''')
  }
}


job("Deploy_job2") {
  description("This Checks the language of code and deploys it on respective interpreter image.")

  triggers {
    upstream("Github_job1")
  }

  steps {
    shell(readFileFromWorkspace("deploy.sh"))
  }
}

job("Test_job3") {
  description("This job tests whether the page has been deployed or not.")

  triggers {
    upstream("Deploy_job2")
  }

  steps {
    shell(readFileFromWorkspace("test_application.sh"))
  }
}

job("Mailing_job4") {
  description("If site is not working, then it will mail to the developer. ")

  triggers {
    upstream("Test_job3","FAILURE")
  }

  publishers {
        mailer('princerajsdv@gmail.com')
  }
}

buildPipelineView("devops_task6") {
  title("Deploy webpage using Jenkins(auomated by groovy) and K8s")
  selectedJob("Github_job1")
  displayedBuilds(1)
  refreshFrequency(4)
  alwaysAllowManualTrigger(false)
  showPipelineParameters(true)
}
