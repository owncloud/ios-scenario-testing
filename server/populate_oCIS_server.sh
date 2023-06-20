echo "Let's populate"

curl -k -u admin:admin $1/graph/v1.0/users -d'{"onPremisesSamAccountName":"alice","displayName":"Alice","mail":"alice@own.com","passwordProfile":{"password":"a"}}' -X POST
curl -k -u admin:admin $1/graph/v1.0/users -d'{"onPremisesSamAccountName":"bob","displayName":"Bob","mail":"bob@own.com","passwordProfile":{"password":"a"}}' -X POST
curl -k -u admin:admin $1/graph/v1.0/users -d'{"onPremisesSamAccountName":"charles","displayName":"Charles","mail":"charly@own.com","passwordProfile":{"password":"a"}}' -X POST

curl -k -u admin:admin $1/graph/v1.0/users -d'{"onPremisesSamAccountName":"user1","displayName":"Joeh Smith","mail":"a@a.com","passwordProfile":{"password":"a"}}' -X POST
curl -k -u admin:admin $1/graph/v1.0/users -d'{"onPremisesSamAccountName":"user2","displayName":"user2","mail":"a@a.com","passwordProfile":{"password":"a"}}' -X POST
curl -k -u admin:admin $1/graph/v1.0/users -d'{"onPremisesSamAccountName":"user3","displayName":"user3","mail":"a@a.com","passwordProfile":{"password":"a"}}' -X POST


#Get id of the users to assing
#id1="$(curl -k -u alice:a $1/graph/v1.0/me | cut -d "," -f 2 | cut -d ":" -f 2 | sed -e 's/^"//' -e 's/"$//')"
#id2="$(curl -k -u bob:a $1/graph/v1.0/me | cut -d "," -f 2 | cut -d ":" -f 2 | sed -e 's/^"//' -e 's/"$//')"
#id3="$(curl -k -u charles:a $1/graph/v1.0/me | cut -d "," -f 2 | cut -d ":" -f 2 | sed -e 's/^"//' -e 's/"$//')"


# Create group and get id of the group to assing
idgroup="$(curl -k -u admin:admin $1/graph/v1.0/groups -d'{"displayName":"test"}' | cut -d "," -f 3 | cut -d ":" -f 2 )"
# Remove string leftovers
idgroup=${idgroup:1:36}

