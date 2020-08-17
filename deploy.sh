sudo cd /root/jenkins_groovy
sudo ls
export len1=\$(ls -l /var/lib/jenkins/workspace/deploy_job2 | grep html | wc -l)

if [ \$len1 -gt 0 ]
then
	export len2=\$(sudo kubectl get deployments | grep webserver | wc -l)
	if [ \$len2 -gt 0 ]
	then
		sudo kubectl rollout restart deployment/webserver
		sudo kubectl rollout status deployment/webserver
  else
		sudo kubectl create deployment webserver --image=prince12/httpd-server:latest
		sudo kubectl scale deployment webserver --replicas=3
		sudo kubectl expose deployment webserver --port 80 --type NodePort
	fi
fi
