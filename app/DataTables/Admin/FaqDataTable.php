<?php
namespace App\DataTables\Admin;

use App\Models\faq;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class FaqDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->addColumn('action', static function ($query){
                return '<a href="'.route('admin.settings.faq.edit', $query).'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>' .
                    '<div style="cursor: pointer" onclick="confirmDelete('.$query->id.')" class="rounded mx-1 btn-danger btn"><i class="ri-delete-bin-fill"></i></div>';
            })
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            });
    }

    public function query(faq $model)
    {
        return $model
            ->newQuery();
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('kitchen-table')
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
            Column::make('question_en')->title(__('web/admin/datatable.faq.question.en')),
            Column::make('question_ar')->title(__('web/admin/datatable.faq.question.ar')),
            Column::make('answer_en')->title(__('web/admin/datatable.faq.answer.en')),
            Column::make('answer_ar')->title(__('web/admin/datatable.faq.answer.ar')),
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
        return 'restaurant_types_' . date('YmdHis');
    }
}
