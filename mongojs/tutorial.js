use mydb

show dbs
db.dropDatabase()

/*******************
// Create Collection , drop collection
*******************/

use mydb
db.createCollection("myCollection")
show collections
db.myCollection2.insert({"name":"Jignesh"})
show collections
db.myCollection.drop()
show collections
db.myCollection2.drop()
show collections


/*******************
// Insert Documents
*******************/


use school
db.students.insert(
    {
        "StudentNo":"1",
        "FirstName":"Jignesh",
        "LastName":"Kapadiya",
        "Age":"10"
    }
)

db.students.insert(
[
    {
        "StudentNo":"2",
        "FirstName":"Aaaaa",
        "LastName":"Bbbbb",
        "Age":"12"
    },
    {
        "StudentNo":"3",
        "FirstName":"Ccccc",
        "LastName":"Ddddd",
        "Age":"15"
    },
    {
        "StudentNo":"4",
        "FirstName":"Eeeee",
        "LastName":"Fffff",
        "Age":"16"
    },
    {
        "StudentNo":"5",
        "FirstName":"Ggggg",
        "LastName":"Hhhhh",
        "Age":"16"
    },
    {
        "StudentNo":"6",
        "FirstName":"Iiiii",
        "LastName":"Jjjjjj",
        "Age":"17"
    },
    {
        "StudentNo":"7",
        "FirstName":"Kkkkk",
        "LastName":"Llllll",
        "Age":"19"
    },
    {
        "StudentNo":"8",
        "FirstName":"Mmmmm",
        "LastName":"Nnnnn",
        "Age":"20"
    },
    {
        "StudentNo":"9",
        "FirstName":"Ooooo",
        "LastName":"Ppppp",
        "Age":"20"
    },
    {
        "StudentNo":"10",
        "FirstName":"Qqqqq",
        "LastName":"Rrrrr",
        "Age":"49"
    }
]
)
/*******************
/// Query Document
*******************/

use school

db.students.find()
db.students.find().pretty()
db.students.findOne()
db.students.find(
{
    "StudentNo": "2"
}
)

db.students.find(
{
    "Age": {$gte: "15"}
}
)

db.students.find(
{
    "Age": {$lte : "15"}
}
)


/*******************
//// Query Document   AND OR condition
*******************/

use school
db.students.find()
// AND
db.students.find(
{"FirstName":"Jignesh","Age":"10"}
)

// OR

db.students.find(
{
    $or : [{"FirstName":"Jignesh"},{"Age":"16"}]
}
)

// AND OR

db.students.find(
{
   "FirstName":"Jignesh", $or : [{"Age":"10"},{"Age":"16"}]
}
)

/******************
/// Update Document
*******************/
use school

db.students.find()

db.students.update(
{"_id":ObjectId("5d8e7a5eb49da3722fee7ad9")},
{$set:{"LastName":"Kapadiya2"}}
)

// it only updates first record it comes across. to update all matching records use "multi"

db.students.update(
{"Age":"16"},
{$set:{"LastName":"NewSurname"}}
)

db.students.find()

// multi:true -> updates all matching records 
db.students.update(
{"Age":"16"},
{$set:{"LastName":"New"}},
{multi:true}
)

db.students.find()

db.students.update(
{"Age":"16"},
{$set:{"LastName":"changedsuranme"}},
{multi:true}
)

db.students.find()

// Save command is also used to update data
// Save command creates a new document if it does not find the id specified.

db.students.save(
{ 
    "_id" : ObjectId("5d8e7a5eb49da3722fee7ad9"), 
    "StudentNo" : "1", 
    "FirstName" : "Jignesh", 
    "LastName" : "Kapadiya2", 
    "Age" : "14"
}
)

db.students.find()

db.students.save(
{ 
    "_id" : ObjectId("5d8e7a5eb49da3722fee7ae3"), 
    "StudentNo" : "1", 
    "FirstName" : "Harry", 
    "LastName" : "Potter", 
    "Age" : "17"
}
)

/*************
////   Delete document
*************/

db.students.find()

// This will remove all documents (no arguments will remove all documents)
//db.students.remove()

// 

db.students.remove(
{ "_id" : ObjectId("5d8e7a5eb49da3722fee7ae3")
}
)

db.students.find()
// Removed all documents with age 16
db.students.remove(
{ "Age":"16"}
)
// This will remove only one document
db.students.remove(
{ "Age":"16"} , 1
)

/*************
// Projection 
*************/

// projection means selecting only necessary data rather than selecting whole of the data of a document

db.students.find()

db.students.find({},{"FirstName":1})

db.students.find({},{"FirstName":1,"_id":0})

/*************
// Sort Skip Limit
*************/

db.students.find()
db.students.find({}, {"StudentNo":1, "FirstName":1,"_id":0})

// limit
db.students.find({}, {"StudentNo":1, "FirstName":1,"_id":0}).limit(4)


db.students.find()
// Skip first 2 documents
db.students.find({}, {"StudentNo":1, "FirstName":1,"_id":0}).skip(2)

// Skip first 2 and limit to 3 records
db.students.find({}, {"StudentNo":1, "FirstName":1,"_id":0}).skip(2).limit(3)

// Sorting in the order of firstname (1 for ascending)
db.students.find({}, {"StudentNo":1, "FirstName":1,"_id":0}).sort({"FirstName":1})

// Sorting in the order of firstname (-1 for descending)
db.students.find({}, {"StudentNo":1, "FirstName":1,"_id":0}).sort({"FirstName":-1})

/*************
 Indexing 
**************/

use temp

for(i=0;i<1000000;i++)
{
    db.posts.insert({"student_id":i,"name":"Jignesh"});
}


db.posts.find()

db.posts.find({"student_id":100000})

db.posts.findOne({"student_id":10000})


db.posts.ensureIndex({"student_id":1})

db.posts.find({"student_id":100000})

db.posts.getIndexes()

/*******************
///  Aggregation
********************/

use school

db.students.find()

// First add new field Gender in the collection
//db.students.update({},{$set:{"Gender":"Male"}}, {upsert:false, multi:true})
//db.students.update({"FirstName":"Qqqqq"},{$set:{"Gender":"Female"}})

// group by gender , find number of documents
db.students.aggregate([{$group: {_id:"$Gender",MyResult:{$sum:1}}}])

// Group by gender, find max age
db.students.aggregate([{$group: {_id:"$Gender",MyResult:{$max:"$Age"}}}])

// Group by gender, find min age
db.students.aggregate([{$group: {_id:"$Gender",MyResult:{$min:"$Age"}}}])






use words
db.wordscount.find({},{"_id":0}).sort({"Count":-1})

db.wordscount.aggregate([{$group: {_id:"$Word",MyResult:{$sum:1}}}])

/// check duplicates values for column word
db.wordscount.aggregate([{$group: {_id:"$Word",count:{$sum:1}}},{$match: {count:{$gt:1}}}
]
)

//78067
db.wordscount.count()

db.dropDatabase()


use floowtask
db.wordscount.find({},{"_id":0}).sort({"Count":-1})
db.wordscount.aggregate([{$group: {_id:"$Word",count:{$sum:1}}},{$match: {count:{$gt:1}}}
]
)
db.wordscount.count()
db.dropDatabase()






use floowtask1
db.wordscount.find({},{"_id":0}).sort({"Count":-1})
db.wordscount.aggregate([{$group: {_id:"$Word",count:{$sum:1}}},{$match: {count:{$gt:1}}}
]
)
db.wordscount.count()
db.dropDatabase()

/**** Shard a collection ****/

sh.status()
sh.enableSharding("floowtask1")
db.wordscount.ensureIndex({_id:"hashed"})
db.wordscount.createIndex({"_id":"hashed"})
sh.shardCollection("floowtask1.wordscount",{"_id":"hashed"})

/** Sharding***/
use mydb
db.createCollection("mycol")
show collections
sh.enableSharding("mydb")
use admin
//db.runCommand({shardCollection:"mydb.mycol",key:{_id:"hashed"}})
sh.shardCollection("mydb.mycol",{_id:"hashed"})
//db.runCommand({"enableSharding":"mydb"})
db.runCommand({shardCollection:"mydb.mycol",key:{"_id":"hashed"}})



use wordscount
db.wordscount.getIndexes()
db.words.ensureIndex({"word":1})
db.words.find({},{"_id":0}).sort({"count":-1})
db.words.aggregate([{$group: {_id:"$word",count:{$sum:1}}},{$match: {count:{$gt:1}}}])
db.words.count()
db.words.getShardVersion()
db.words.getShardDistribution()
db.words.remove({})
drop()
db.dropDatabase()

db.chunks.size()

/*** replica set for congif server **/

> rs.initiate()
{
        "ok" : 0,
        "errmsg" : "This node was not started with the replSet option",
        "code" : 76,
        "codeName" : "NoReplicationEnabled"
}



/*-------------------------------------------*/

use nothread
//db.createCollection("wordscount")
db.wordscount.find({},{"_id":0}).sort({"count":-1})
db.wordscount.aggregate([{$group: {_id:"$word",count:{$sum:1}}},{$match: {count:{$gt:1}}}])
db.wordscount.count()
//db.wordscount.remove({})
db.wordscount.ensureIndex({"word":1})
db.wordscount.getIndexes()
db.dropDatabase()




use abcde
db.createCollection("xyz")
show collections

db.xyz.createIndex({"word":"hashed"})
db.xyz.getIndexes()

db.xyz.find({},{"_id":0}).sort({"count":-1})
db.xyz.aggregate([{$group: {_id:"$word",count:{$sum:1}}},{$match: {count:{$gt:1}}}])
db.xyz.count()

db.xyz.drop()
db.dropDatabase()
//----------------------------------------------------

use wordsdb
//db.createCollection("xyz")
show collections

db.wordscount.createIndex({"word":"hashed"})
db.wordscount.getIndexes()

db.wordscount.find({},{"_id":0}).sort({"count":-1})
db.wordscount.find({},{"_id":0}).sort({"count":-1}).limit(1)
db.wordscount.find({},{"_id":0}).sort({"count":1}).limit(1)
db.wordscount.aggregate([{$group: {_id:"$word",count:{$sum:1}}},{$match: {count:{$gt:1}}}], {allowDiskUse:true})
db.wordscount.count()

use admin
db.runCommand({getParameter:1,"internalQueryExecMaxBlockingSortBytes" : 1 })
db.adminCommand({setParameter: 1, internalQueryExecMaxBlockingSortBytes:309715200})


db.wordscount.drop()
db.dropDatabase()
//----------------------------------------------------


use nothreadfinal
//db.createCollection("wordscount")
db.wordscount.find({},{"_id":0}).sort({"count":-1})
db.wordscount.aggregate([{$group: {_id:"$word",count:{$sum:1}}},{$match: {count:{$gt:1}}}])
db.wordscount.count()
//db.wordscount.remove({})
db.wordscount.ensureIndex({"word":1})
db.wordscount.getIndexes()
db.dropDatabase()

count = 213248
{ "word" : "the", "count" : NumberLong(206267) }
{ "word" : "of", "count" : NumberLong(157796) }
{ "word" : "and", "count" : NumberLong(112235) }
{ "word" : "gt", "count" : NumberLong(108532) }
{ "word" : "lt", "count" : NumberLong(108435) }
{ "word" : "ref", "count" : NumberLong(92148) }
{ "word" : "quot", "count" : NumberLong(90393) }
{ "word" : "in", "count" : NumberLong(81589) }
{ "word" : "to", "count" : NumberLong(69756) }
{ "word" : "a", "count" : NumberLong(60525) }
{ "word" : "The", "count" : NumberLong(40921) }
{ "word" : "amp", "count" : NumberLong(35807) }
{ "word" : "title", "count" : NumberLong(34383) }
{ "word" : "s", "count" : NumberLong(33826) }
{ "word" : "url", "count" : NumberLong(30736) }
{ "word" : "is", "count" : NumberLong(30519) }
{ "word" : "first", "count" : NumberLong(27797) }
{ "word" : "web", "count" : NumberLong(27232) }
{ "word" : "name", "count" : NumberLong(26995) }
{ "word" : "as", "count" : NumberLong(26609) }


db.version()
