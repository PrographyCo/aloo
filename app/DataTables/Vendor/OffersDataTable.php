<?php
namespace App\DataTables\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Drink;
use App\Models\Offer;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class OffersDataTable extends DataTable
{
    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('amount', static function ($query){
                return $query->amount . ' ' . __('web/vendor/datatable.extra.calories');
            })
            ->editColumn('img', static function ($query){
                return '<a href="'.$query->img.'">Show Image</a>';
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('vendor.offer.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'action',
                'img'
            ]);
    }


    public function query(Offer $model)
    {
        return $model
            ->newQuery()
            ->where('vendor_id', \Auth::id());
    }


    public function html()
    {
        return $this->builder()
            ->setTableId('drink-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('name_en')->title('name_en'),
            Column::make('description_en')->title('description_en'),
            Column::make('name_ar')->title('name_ar'),
            Column::make('description_ar')->title('description_ar'),
            Column::make('price')->title(__('web/vendor/datatable.drink.price')),
            Column::make('amount')->title(__('web/vendor/datatable.drink.calories')),
            Column::make('drinks')->title('drinks'),
            Column::make('extras')->title('extras'),
            Column::make('size')->title('Size'),
            Column::make('with')->title('With'),
            Column::make('without')->title('Without'),
            Column::make('img')->title('Image'),
            Column::make('created_at')->title(__('web/vendor/datatable.item.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'drinks_' . date('YmdHis');
    }
}
