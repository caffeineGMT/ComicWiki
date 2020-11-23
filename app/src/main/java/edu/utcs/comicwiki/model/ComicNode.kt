package edu.utcs.comicwiki.model

data class ComicNode(
    var fromNode: ComicNode? = null,
    var toNode: ComicNode? = null,
    var name: String? = null,
    var deck: String? = null,
    var smallImageURL: String? = null,
    var largeImageURL: String? = null,
    var apiDetailURL: String? = null
)