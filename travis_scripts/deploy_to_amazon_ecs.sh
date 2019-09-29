echo "Launching $BUILD_NAME IN AMAZON ECS"
# ecs-cli configure --region us-west-2 --access-key $AWS_ACCESS_KEY --secret-key $AWS_SECRET_KEY --cluster sms-fintech-ecs
ecs-cli configure profile --profile-name sms-cap-10 --access-key $AWS_ACCESS_KEY --secret-key $AWS_SECRET_KEY 
ecs-cli configure --cluster sms-fintech-ecs --default-launch-type EC2 --region 	us-west-2 --config-name config-sms-fintech-ecs

ecs-cli compose --file docker/docker-compose-travis.yml up
rm -rf ~/.ecs


