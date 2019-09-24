# BloomFilter-CMS

In this repo, I have designed a Bloom Filter and implemented the count min sketch data structure.

Bloom Filter is a probabilistic, memory-efficient data structure to store a set $S$. Different types of Bloom Filters in this program are:
1) BloomFilterFNV: deterministic hash function 64-bit FNV
2) BloomFilterRan: uses random Fuction as hash function
3) BloomFilterMurmur: use murmurhash function instead of FNV
4) BloomDifferential: Bloom filters can be used in some low-level applications that involve file management. It is very inefficient to make changes to the database files every time a record is changed.  A better approach is to create a differentialfile. This method provides a solution for this issue.
5) CMS: Count min sketch structure to store a multi-set S.
