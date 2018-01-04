# iot_

W folderze repository/SystemRepository znajdziemy folder z projektem Smart Flowers.
Tam są datashapes, mashups, projects i things.

Wywołanie HTTP do naszych Thingów

np.

Thing: ambient_temperature ma wartość temp_value i id

GET:
localhost/Thingworx/Things/ambient_temperature/Properties/temp_value

POST:
localhost/Thingworx/Things/ambient_temperature/Properties/temp_value

JSON:
{
"temp_value" : "34.4"
}

Tylko to mi niestety nadpisuje jak dodam 2x coś :/ Trzeba oblukać jak robić z tego zbiór danych.