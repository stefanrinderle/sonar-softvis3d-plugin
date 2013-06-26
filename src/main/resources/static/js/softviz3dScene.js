if (!Detector.webgl)
		Detector.addGetWebGLMessage();

	var container, stats;

	var camera, controls, scene, renderer;

	var cross;

	camera = new THREE.PerspectiveCamera(60, window.innerWidth
			/ window.innerHeight, 1, 10000);
	camera.position.set(0, 3000, 3000)

	controls = new THREE.OrbitControls(camera);
	controls.addEventListener('change', render);

	scene = new THREE.Scene();

	// lights

	var light = new THREE.DirectionalLight( 0xaaaaaa);
    light.position.set( 1, 0, 0 ).normalize();
    scene.add( light );

    var light = new THREE.DirectionalLight( 0xcccccc);
    light.position.set( -1, 0, 0 ).normalize();
    scene.add( light );
    
    var light = new THREE.DirectionalLight( 0xddddddd);
    light.position.set( 0, 0, 1 ).normalize();
    scene.add( light );
    
    var light = new THREE.DirectionalLight( 0xeeeeee);
    light.position.set( 0, 0, -1 ).normalize();
    scene.add( light );
    
    var directionalLight = new THREE.DirectionalLight( 0xffffff);
    directionalLight.position.set( 0, 1, 0 );
    scene.add( directionalLight );
    
	// renderer

	renderer = new THREE.WebGLRenderer({
		antialias : true
	});
	renderer.setClearColor(0xffffff, 1);

	// create center cube for better navigation

	var centerCube = new THREE.CubeGeometry(10, 10, 10);
	var greenMaterial = new THREE.MeshBasicMaterial({
		color : 0x00ff00
	});
	position = new Array();
	position.x = 0;
	position.y = 0;
	position.z = 0;

	var mesh = new THREE.Mesh(centerCube, greenMaterial);
	mesh.position.x = position.x;
	mesh.position.y = position.y;
	mesh.position.z = position.z;
	mesh.updateMatrix();
	mesh.matrixAutoUpdate = false;

	scene.add(mesh);

	// append scene to html container

	container = document.getElementById('renderContainer');
	container.appendChild(renderer.domElement);

	// show stats
	stats = new Stats();
	stats.domElement.style.zIndex = 100;
	container.appendChild(stats.domElement);

	window.addEventListener('resize', onWindowResize, false);
	onWindowResize()

	animate();
	
	function onWindowResize() {
		camera.aspect = window.innerWidth / window.innerHeight;
		camera.updateProjectionMatrix();

		// header of sonar is 70 px - footer 50 px - sidebar 200px
		renderer.setSize(window.innerWidth - 200, window.innerHeight - 120);

		render();
	}

	function animate() {

		requestAnimationFrame(animate);
		controls.update();

	}
	
	function render() {
		renderer.render(scene, camera);
		stats.update();
	}

	function createBox(geometry1, material1, position1) {
		var mesh1 = new THREE.Mesh(geometry1, material1);
		mesh1.position.x = position1.x;
		mesh1.position.y = position1.y;
		mesh1.position.z = position1.z;
		mesh1.updateMatrix();
		mesh1.matrixAutoUpdate = false;

		scene.add(mesh1);
	}

	function drawText(textbla, position2, size) {
        /////// draw text on canvas /////////

        // create a canvas element
        var canvas1 = document.createElement('canvas');
        var context1 = canvas1.getContext('2d');
        
        context1.font = "Bold " + size + "px Arial";
        //context1.fillStyle = "rgba(255,0,0,0.95)";
        context1.fillText(textbla, 0, size);

        var value = context1.measureText(textbla).width;
        canvas1.width = value;
        canvas1.height = size;
        
        context1.font = size + "px Arial";
        context1.textBaseline = "bottom";
        context1.fillStyle = "rgba(0,0,0,0.95)";
        context1.fillText(textbla, 0, size);
        
        
        // canvas contents will be used for a texture
        var texture1 = new THREE.Texture(canvas1) 
        texture1.needsUpdate = true;
          
        var material1 = new THREE.MeshBasicMaterial( {map: texture1, side:THREE.DoubleSide } );
        material1.transparent = true;

        var mesh1 = new THREE.Mesh(
            new THREE.PlaneGeometry(canvas1.width, canvas1.height),
            material1
          );
        mesh1.position = position2
        scene.add( mesh1 );
    }