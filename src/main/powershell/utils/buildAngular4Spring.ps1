$src = "typescript\angularclient\dist\angularclient"
$dest = "resources\public\"
cd ..\..\

cd typescript\angularclient
ng build --prod
cd ..\..\

rm $dest -Force -Recurse -ErrorAction SilentlyContinue
cp $src $dest -Recurse
