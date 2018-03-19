#!/bin/bash
command="INSERT IGNORE INTO companyId (id,name) VALUES "
for i in {1..1000}
do
   command="$command, ($i,CONCAT('companyTest',$i))"
done
echo $command > insertCompany.sql