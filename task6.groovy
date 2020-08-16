job("Pull from GitHub") {
  description("Pulls Codes and scripts From GitHub repository and copies them into a folder in base system.")

  scm {
    github("princerajsdv81/devops_task6", "master")
  }

  triggers {
    scm("* * * * *")
  }

  steps {
    shell('''
    sudo cp -rvf * /home/groovy
    ''')
  }
}

job("Deploy page") {
  description("Checks the language of code and deploys it on respective interpreter image.")

  triggers {
    upstream("Pull from GitHub")
  }

  steps {
    shell(readFileFromWorkspace("deploy.sh"))
  }
}

job("Test job") {
  description("Tests whether the page has been deployed or not.")

  triggers {
    upstream("Deploy page")
  }

  steps {
    shell(readFileFromWorkspace("test_application.sh"))
  }
}

job("Mailing") {
  description("If site is not working, then it will mail to the developer. ")

  triggers {
    upstream("Test job","FAILURE")
  }

  publishers {
        mailer('princerajsdv@gmail.com')
  }
}

buildPipelineView("devops_task6") {
  title("Deploy webpage using Jenkins(auomated by groovy) and K8s")
  selectedJob("Pull from GitHub")
  displayedBuilds(1)
  refreshFrequency(4)
  alwaysAllowManualTrigger(false)
  showPipelineParameters(true)
}
