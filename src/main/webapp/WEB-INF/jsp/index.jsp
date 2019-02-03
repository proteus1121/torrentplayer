<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Spring Boot - Streaming Response Body">
    <meta name="author" content="Shazin Sadakath">
    <link href="https://ghinda.net/acornmediaplayer/acornmediaplayer/acornmediaplayer.base.css" rel="stylesheet"
          type="text/css">
    <link href="https://ghinda.net/acornmediaplayer/acornmediaplayer/themes/access/acorn.access.css" rel="stylesheet"
          type="text/css">
    <script>
        const SHOWING = 'showing';
        const HIDDEN = 'hidden';
    </script>

    <title>Spring Boot - Streaming Response Body</title>
    <style type="text/css">
        body {
            background-color: #fdfdfd;
            padding: 0 20px;
            color: #000;
            font: 13px/18px monospace;
            width: 800px;
        }

        ::cue(.de) {
            color: #000;
            background-color: #ffcc00;
        }

        ::cue(.en) {
            color: #fff;
            background-color: #00247d;
        }

        ::cue(.es) {
            color: #f1bf00;
            background-color: #aa151b;
        }

        a {
            color: #360;
        }

        h3 {
            padding-top: 20px;
        }
    </style>

    <!-- Bootstrap core CSS -->
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/resources/css/ie10-viewport-bug-workaround.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/resources/css/jumbotron-narrow.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation" class="active"><a href="#">Home</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">Streaming Response Body</h3>
    </div>

    <figure>
        <video id="video" controls="controls" width="600" height="400" preload="metadata"
               aria-describedby="videodescription">

        </video>
        <figcaption id="videodescription">
            Some description of video.
        </figcaption>
    </figure>
    <button id="show-all">Show all</button>

    <div class="row marketing">

        <div class="col-lg-6">
            <h4>Playlist</h4>
            <p>
            <div id="playlists">
            			<c:forEach var="listValue" items="${filmList}">
            				<a href="/${listValue}">${listValue}</a></br>
            			</c:forEach>
            </div>
            </p>
        </div>
    </div>
     <script src="https://cdn.jsdelivr.net/npm/webtorrent@latest/webtorrent.min.js"></script>
     <script>

        function switchSubtitles(track) {
            if (track.mode === SHOWING){
                track.mode = HIDDEN;
            }
            else{
                track.mode = SHOWING;
            }
        }

        document.getElementById('show-all').addEventListener('click', function () {
            for (var i = 0; i < video.textTracks.length; i++) {
                switchSubtitles(video.textTracks[i]);
            }
        });

var torrentId = 'magnet:?xt=urn:btih:08ada5a7a6183aae1e09d831df6748d566095a10&dn=Sintel&tr=udp%3A%2F%2Fexplodie.org%3A6969&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.empire-js.us%3A1337&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=wss%3A%2F%2Ftracker.btorrent.xyz&tr=wss%3A%2F%2Ftracker.fastcast.nz&tr=wss%3A%2F%2Ftracker.openwebtorrent.com&ws=https%3A%2F%2Fwebtorrent.io%2Ftorrents%2F&xs=https%3A%2F%2Fwebtorrent.io%2Ftorrents%2Fsintel.torrent'

var videoP = document.getElementById('video');
var client = new WebTorrent()

client.add(torrentId, function (torrent) {
  // Got torrent metadata!
  console.log('Torrent info hash:', torrent.infoHash)

  // Let's say the first file is a webm (vp8) or mp4 (h264) video...
  var file = torrent.files.find(function (file) {
            return file.name.endsWith('.mp4')
          })

  // Stream the video into the video tag
  file.createReadStream().pipe(videoP)
})
    </script>

    <footer class="footer">
        <p>&copy; 2015</p>
    </footer>

</div>
<!-- /container -->

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/resources/js/ie10-viewport-bug-workaround.js"></script>

<!-- Load jquery -->
<script type="text/javascript" src="/resources/jquery-1.9.1.min.js"></script>


</body>
</html>
