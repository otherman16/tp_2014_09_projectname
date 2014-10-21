define([
	// Libs
	'jquery',
	'backbone',
	'vertex_collection'
], function($, Backbone, models){
	var VertexView = Backbone.View.extend({

		render: function() {
            this.canvas = document.getElementById("myCanvas");
            this.c = this.canvas.getContext('2d');
            // очистить экран
            this.c.clearRect(0, 0, this.canvas.width, this.canvas.height);
            this.c.beginPath();
            var prev = models.last();
            this.c.moveTo(prev.get('x'), prev.get('y'));
            // Рисуем линии между вершинами
            models.each(function(vertex){
                var canvas = document.getElementById("myCanvas");
                var c = canvas.getContext('2d');
                c.lineTo(vertex.get('x'), vertex.get('y'));
            });
            this.c.closePath();
            this.c.fillStyle = 'gray';
            this.c.fill();
            this.c.stroke();
            // Отрисовываем каждую вершину
            models.each(function(vertex){
                var canvas = document.getElementById("myCanvas");
                var c = canvas.getContext('2d');
                var x = vertex.get('x'),
                    y = vertex.get('y'),
                    radius = vertex.radius;
                c.beginPath();
                c.arc(x, y, radius, 0, 2 * Math.PI);
                c.closePath();
                if ( vertex.get('who') == "i" )
                    c.fillStyle = 'black';
                else
                    c.fillStyle = 'red';
                c.fill();
                console.log(this);
            });

          },

		initialize: function() {
		     models.on('add', this.render);
             models.on('remove', this.render);
             models.on('change', this.render);
		}
	});
	return VertexView;
})
