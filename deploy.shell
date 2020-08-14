sudo cd /home/groovy
sudo ls
if sudo kubectl get all|grep webdeploy
then
sudo kubectl delete all --all
sudo kubectl delete pvc --all
sudo kubectl create -f /home/groovy/webdeploy.yml
sleep 10
sudo kubectl get all
else
sudo kubectl create -f /home/groovy/webdeploy.yml
sleep 10
sudo kubectl get all
fi
sudo kubectl cp /home/groovy/index.html $(sudo kubectl get pod|grep webdeploy| awk '{print $1}'):/var/www/html
