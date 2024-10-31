mkdir minio_data
chmod 777 minio_data
docker compose up -d
echo "Pronto, agora o Minio está rodando em http://localhost:9000 e vai iniciar com o container junto com o SO."
docker compose logs -f