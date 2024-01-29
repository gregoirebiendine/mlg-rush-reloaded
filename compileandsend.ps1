# Run Maven package
mvn package

# Command to send target/MLGRushReloaded-1.0.jar with scp to 2001:470:ca78:12:2069:15ff:fe70:e2d4 (it is an ipv6)

# Define the source and destination paths
$sourcePath = "target\MLGRushReloaded-1.0.jar"
$destinationPath = "/home/minecraft/server/plugins/MLGRushReloaded-1.0.jar"

# Use scp to copy the file
& "C:\Program Files\Git\usr\bin\scp.exe" -6 $sourcePath "minecraft@[2001:470:ca78:12:2069:15ff:fe70:e2d4]:$destinationPath"
